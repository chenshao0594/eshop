import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductOptionValueDescription } from './product-option-value-description.model';
import { ProductOptionValueDescriptionService } from './product-option-value-description.service';

@Component({
    selector: 'jhi-product-option-value-description-detail',
    templateUrl: './product-option-value-description-detail.component.html'
})
export class ProductOptionValueDescriptionDetailComponent implements OnInit, OnDestroy {

    productOptionValueDescription: ProductOptionValueDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productOptionValueDescriptionService: ProductOptionValueDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productOptionValueDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductOptionValueDescriptions();
    }

    load(id) {
        this.productOptionValueDescriptionService.find(id).subscribe((productOptionValueDescription) => {
            this.productOptionValueDescription = productOptionValueDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productOptionValueDescription.id !== undefined) {
            this.productOptionValueDescriptionService.update(this.productOptionValueDescription)
                .subscribe((res: ProductOptionValueDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productOptionValueDescriptionService.create(this.productOptionValueDescription)
                .subscribe((res: ProductOptionValueDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductOptionValueDescription) {
        this.eventManager.broadcast({ name: 'productOptionValueDescriptionModification', content: 'OK'});
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

    registerChangeInProductOptionValueDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('productOptionValueDescriptionListModification', (response) => this.load(this.productOptionValueDescription.id));
    }
}
