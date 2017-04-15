import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductImageDescription } from './product-image-description.model';
import { ProductImageDescriptionService } from './product-image-description.service';
@Injectable()
export class ProductImageDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productImageDescriptionService: ProductImageDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productImageDescriptionService.find(id).subscribe((productImageDescription) => {
                this.productImageDescriptionModalRef(component, productImageDescription);
            });
        } else {
            return this.productImageDescriptionModalRef(component, new ProductImageDescription());
        }
    }

    productImageDescriptionModalRef(component: Component, productImageDescription: ProductImageDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productImageDescription = productImageDescription;
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
