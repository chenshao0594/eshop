import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductAttribute } from './product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';
@Injectable()
export class ProductAttributePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productAttributeService: ProductAttributeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productAttributeService.find(id).subscribe((productAttribute) => {
                this.productAttributeModalRef(component, productAttribute);
            });
        } else {
            return this.productAttributeModalRef(component, new ProductAttribute());
        }
    }

    productAttributeModalRef(component: Component, productAttribute: ProductAttribute): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productAttribute = productAttribute;
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
