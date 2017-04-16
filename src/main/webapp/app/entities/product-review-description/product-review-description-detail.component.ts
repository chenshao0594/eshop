import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductReviewDescription } from './product-review-description.model';
import { ProductReviewDescriptionService } from './product-review-description.service';

@Component({
    selector: 'jhi-product-review-description-detail',
    templateUrl: './product-review-description-detail.component.html'
})
export class ProductReviewDescriptionDetailComponent implements OnInit, OnDestroy {

    productReviewDescription: ProductReviewDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productReviewDescriptionService: ProductReviewDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productReviewDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductReviewDescriptions();
    }

    load(id) {
        this.productReviewDescriptionService.find(id).subscribe((productReviewDescription) => {
            this.productReviewDescription = productReviewDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productReviewDescription.id !== undefined) {
            this.productReviewDescriptionService.update(this.productReviewDescription)
                .subscribe((res: ProductReviewDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productReviewDescriptionService.create(this.productReviewDescription)
                .subscribe((res: ProductReviewDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductReviewDescription) {
        this.eventManager.broadcast({ name: 'productReviewDescriptionModification', content: 'OK'});
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

    registerChangeInProductReviewDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('productReviewDescriptionListModification', (response) => this.load(this.productReviewDescription.id));
    }
}
