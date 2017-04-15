import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductOptionDescription } from './product-option-description.model';
import { ProductOptionDescriptionService } from './product-option-description.service';
@Injectable()
export class ProductOptionDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productOptionDescriptionService: ProductOptionDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productOptionDescriptionService.find(id).subscribe((productOptionDescription) => {
                this.productOptionDescriptionModalRef(component, productOptionDescription);
            });
        } else {
            return this.productOptionDescriptionModalRef(component, new ProductOptionDescription());
        }
    }

    productOptionDescriptionModalRef(component: Component, productOptionDescription: ProductOptionDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productOptionDescription = productOptionDescription;
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
