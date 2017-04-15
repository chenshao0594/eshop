import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductReview } from './product-review.model';
import { ProductReviewService } from './product-review.service';
@Injectable()
export class ProductReviewPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productReviewService: ProductReviewService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productReviewService.find(id).subscribe((productReview) => {
                if (productReview.reviewDate) {
                    productReview.reviewDate = {
                        year: productReview.reviewDate.getFullYear(),
                        month: productReview.reviewDate.getMonth() + 1,
                        day: productReview.reviewDate.getDate()
                    };
                }
                this.productReviewModalRef(component, productReview);
            });
        } else {
            return this.productReviewModalRef(component, new ProductReview());
        }
    }

    productReviewModalRef(component: Component, productReview: ProductReview): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productReview = productReview;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
