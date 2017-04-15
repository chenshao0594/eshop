import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { TaxClass } from './tax-class.model';
import { TaxClassService } from './tax-class.service';

@Component({
    selector: 'jhi-tax-class-detail',
    templateUrl: './tax-class-detail.component.html'
})
export class TaxClassDetailComponent implements OnInit, OnDestroy {

    taxClass: TaxClass;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private taxClassService: TaxClassService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['taxClass']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTaxClasses();
    }

    load(id) {
        this.taxClassService.find(id).subscribe((taxClass) => {
            this.taxClass = taxClass;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.taxClass.id !== undefined) {
            this.taxClassService.update(this.taxClass)
                .subscribe((res: TaxClass) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.taxClassService.create(this.taxClass)
                .subscribe((res: TaxClass) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: TaxClass) {
        this.eventManager.broadcast({ name: 'taxClassModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTaxClasses() {
        this.eventSubscriber = this.eventManager.subscribe('taxClassListModification', (response) => this.load(this.taxClass.id));
    }
}
