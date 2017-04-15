import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductOptionValueDescription } from './product-option-value-description.model';
import { ProductOptionValueDescriptionService } from './product-option-value-description.service';
@Injectable()
export class ProductOptionValueDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productOptionValueDescriptionService: ProductOptionValueDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productOptionValueDescriptionService.find(id).subscribe((productOptionValueDescription) => {
                this.productOptionValueDescriptionModalRef(component, productOptionValueDescription);
            });
        } else {
            return this.productOptionValueDescriptionModalRef(component, new ProductOptionValueDescription());
        }
    }

    productOptionValueDescriptionModalRef(component: Component, productOptionValueDescription: ProductOptionValueDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productOptionValueDescription = productOptionValueDescription;
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
