import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private taxClassService: TaxClassService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTaxClasses() {
        this.eventSubscriber = this.eventManager.subscribe('taxClassListModification', (response) => this.load(this.taxClass.id));
    }
}
