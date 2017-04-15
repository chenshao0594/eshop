import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductReviewDescription } from './product-review-description.model';
import { ProductReviewDescriptionPopupService } from './product-review-description-popup.service';
import { ProductReviewDescriptionService } from './product-review-description.service';

@Component({
    selector: 'jhi-product-review-description-delete-dialog',
    templateUrl: './product-review-description-delete-dialog.component.html'
})
export class ProductReviewDescriptionDeleteDialogComponent {

    productReviewDescription: ProductReviewDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productReviewDescriptionService: ProductReviewDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productReviewDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productReviewDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productReviewDescriptionListModification',
                content: 'Deleted an productReviewDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-review-description-delete-popup',
    template: ''
})
export class ProductReviewDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productReviewDescriptionPopupService: ProductReviewDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productReviewDescriptionPopupService
                .open(ProductReviewDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
