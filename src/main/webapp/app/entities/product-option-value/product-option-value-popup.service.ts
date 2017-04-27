import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductOptionValue } from './product-option-value.model';
import { ProductOptionValueService } from './product-option-value.service';
@Injectable()
export class ProductOptionValuePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productOptionValueService: ProductOptionValueService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productOptionValueService.find(id).subscribe((productOptionValue) => {
                if (productOptionValue.createdDate) {
                    productOptionValue.createdDate = {
                        year: productOptionValue.createdDate.getFullYear(),
                        month: productOptionValue.createdDate.getMonth() + 1,
                        day: productOptionValue.createdDate.getDate()
                    };
                }
                if (productOptionValue.lastModifiedDate) {
                    productOptionValue.lastModifiedDate = {
                        year: productOptionValue.lastModifiedDate.getFullYear(),
                        month: productOptionValue.lastModifiedDate.getMonth() + 1,
                        day: productOptionValue.lastModifiedDate.getDate()
                    };
                }
                this.productOptionValueModalRef(component, productOptionValue);
            });
        } else {
            return this.productOptionValueModalRef(component, new ProductOptionValue());
        }
    }

    productOptionValueModalRef(component: Component, productOptionValue: ProductOptionValue): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productOptionValue = productOptionValue;
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
