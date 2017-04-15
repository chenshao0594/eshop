import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductReview } from './product-review.model';
import { ProductReviewPopupService } from './product-review-popup.service';
import { ProductReviewService } from './product-review.service';
import { Customer, CustomerService } from '../customer';

@Component({
    selector: 'jhi-product-review-dialog',
    templateUrl: './product-review-dialog.component.html'
})
export class ProductReviewDialogComponent implements OnInit {

    productReview: ProductReview;
    authorities: any[];
    isSaving: boolean;

    customers: Customer[];
        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productReviewService: ProductReviewService,
        private customerService: CustomerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productReview']);
        this.productReview = new ProductReview();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerService.query().subscribe(
            (res: Response) => { this.customers = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
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
        this.eventManager.broadcast({ name: 'productReviewListModification', content: 'OK'});
        this.isSaving = false;
        this.productReview = result;
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

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-review-popup',
    template: ''
})
export class ProductReviewPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productReviewPopupService: ProductReviewPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productReviewPopupService
                    .open(ProductReviewDialogComponent, params['id']);
            } else {
                this.modalRef = this.productReviewPopupService
                    .open(ProductReviewDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
