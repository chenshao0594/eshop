import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductReviewDescription } from './product-review-description.model';
import { ProductReviewDescriptionPopupService } from './product-review-description-popup.service';
import { ProductReviewDescriptionService } from './product-review-description.service';
import { ProductReview, ProductReviewService } from '../product-review';

@Component({
    selector: 'jhi-product-review-description-dialog',
    templateUrl: './product-review-description-dialog.component.html'
})
export class ProductReviewDescriptionDialogComponent implements OnInit {

    productReviewDescription: ProductReviewDescription;
    authorities: any[];
    isSaving: boolean;

    productreviews: ProductReview[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productReviewDescriptionService: ProductReviewDescriptionService,
        private productReviewService: ProductReviewService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productReviewDescription']);
        this.productReviewDescription = new ProductReviewDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productReviewService.query().subscribe(
            (res: Response) => { this.productreviews = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
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
        this.eventManager.broadcast({ name: 'productReviewDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.productReviewDescription = result;
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

    trackProductReviewById(index: number, item: ProductReview) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-review-description-popup',
    template: ''
})
export class ProductReviewDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productReviewDescriptionPopupService: ProductReviewDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productReviewDescriptionPopupService
                    .open(ProductReviewDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.productReviewDescriptionPopupService
                    .open(ProductReviewDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
