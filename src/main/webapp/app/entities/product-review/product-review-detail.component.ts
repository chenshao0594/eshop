import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductReview } from './product-review.model';
import { ProductReviewService } from './product-review.service';

@Component({
    selector: 'jhi-product-review-detail',
    templateUrl: './product-review-detail.component.html'
})
export class ProductReviewDetailComponent implements OnInit, OnDestroy {

    productReview: ProductReview;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productReviewService: ProductReviewService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productReview']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductReviews();
    }

    load(id) {
        this.productReviewService.find(id).subscribe((productReview) => {
            this.productReview = productReview;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productReview.id !== undefined) {
            this.productReviewService.update(this.productReview)
                .subscribe((res: ProductReview) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productReviewService.create(this.productReview)
                .subscribe((res: ProductReview) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductReview) {
        this.eventManager.broadcast({ name: 'productReviewModification', content: 'OK'});
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

    registerChangeInProductReviews() {
        this.eventSubscriber = this.eventManager.subscribe('productReviewListModification', (response) => this.load(this.productReview.id));
    }
}
