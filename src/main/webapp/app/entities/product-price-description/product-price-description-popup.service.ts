import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductPriceDescription } from './product-price-description.model';
import { ProductPriceDescriptionService } from './product-price-description.service';
@Injectable()
export class ProductPriceDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productPriceDescriptionService: ProductPriceDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productPriceDescriptionService.find(id).subscribe((productPriceDescription) => {
                this.productPriceDescriptionModalRef(component, productPriceDescription);
            });
        } else {
            return this.productPriceDescriptionModalRef(component, new ProductPriceDescription());
        }
    }

    productPriceDescriptionModalRef(component: Component, productPriceDescription: ProductPriceDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productPriceDescription = productPriceDescription;
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
