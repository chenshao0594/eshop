import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductReviewDescription } from './product-review-description.model';
import { ProductReviewDescriptionService } from './product-review-description.service';
@Injectable()
export class ProductReviewDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productReviewDescriptionService: ProductReviewDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productReviewDescriptionService.find(id).subscribe((productReviewDescription) => {
                this.productReviewDescriptionModalRef(component, productReviewDescription);
            });
        } else {
            return this.productReviewDescriptionModalRef(component, new ProductReviewDescription());
        }
    }

    productReviewDescriptionModalRef(component: Component, productReviewDescription: ProductReviewDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productReviewDescription = productReviewDescription;
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
