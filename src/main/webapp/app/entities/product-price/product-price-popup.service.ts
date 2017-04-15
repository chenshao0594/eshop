import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductPrice } from './product-price.model';
import { ProductPriceService } from './product-price.service';
@Injectable()
export class ProductPricePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private productPriceService: ProductPriceService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.productPriceService.find(id).subscribe((productPrice) => {
                if (productPrice.productPriceSpecialEndDate) {
                    productPrice.productPriceSpecialEndDate = {
                        year: productPrice.productPriceSpecialEndDate.getFullYear(),
                        month: productPrice.productPriceSpecialEndDate.getMonth() + 1,
                        day: productPrice.productPriceSpecialEndDate.getDate()
                    };
                }
                if (productPrice.productPriceSpecialStartDate) {
                    productPrice.productPriceSpecialStartDate = {
                        year: productPrice.productPriceSpecialStartDate.getFullYear(),
                        month: productPrice.productPriceSpecialStartDate.getMonth() + 1,
                        day: productPrice.productPriceSpecialStartDate.getDate()
                    };
                }
                this.productPriceModalRef(component, productPrice);
            });
        } else {
            return this.productPriceModalRef(component, new ProductPrice());
        }
    }

    productPriceModalRef(component: Component, productPrice: ProductPrice): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.productPrice = productPrice;
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
