import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductOptionDescription } from './product-option-description.model';
import { ProductOptionDescriptionService } from './product-option-description.service';

@Component({
    selector: 'jhi-product-option-description-detail',
    templateUrl: './product-option-description-detail.component.html'
})
export class ProductOptionDescriptionDetailComponent implements OnInit, OnDestroy {

    productOptionDescription: ProductOptionDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productOptionDescriptionService: ProductOptionDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productOptionDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductOptionDescriptions();
    }

    load(id) {
        this.productOptionDescriptionService.find(id).subscribe((productOptionDescription) => {
            this.productOptionDescription = productOptionDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productOptionDescription.id !== undefined) {
            this.productOptionDescriptionService.update(this.productOptionDescription)
                .subscribe((res: ProductOptionDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productOptionDescriptionService.create(this.productOptionDescription)
                .subscribe((res: ProductOptionDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductOptionDescription) {
        this.eventManager.broadcast({ name: 'productOptionDescriptionModification', content: 'OK'});
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

    registerChangeInProductOptionDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('productOptionDescriptionListModification', (response) => this.load(this.productOptionDescription.id));
    }
}
