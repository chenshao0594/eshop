import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductReview } from './product-review.model';
import { ProductReviewPopupService } from './product-review-popup.service';
import { ProductReviewService } from './product-review.service';

@Component({
    selector: 'jhi-product-review-delete-dialog',
    templateUrl: './product-review-delete-dialog.component.html'
})
export class ProductReviewDeleteDialogComponent {

    productReview: ProductReview;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productReviewService: ProductReviewService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productReview']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productReviewService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productReviewListModification',
                content: 'Deleted an productReview'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-review-delete-popup',
    template: ''
})
export class ProductReviewDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productReviewPopupService: ProductReviewPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productReviewPopupService
                .open(ProductReviewDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
