import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrderProductPrice } from './order-product-price.model';
import { OrderProductPriceService } from './order-product-price.service';
@Injectable()
export class OrderProductPricePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderProductPriceService: OrderProductPriceService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.orderProductPriceService.find(id).subscribe((orderProductPrice) => {
                if (orderProductPrice.productPriceSpecialStartDate) {
                    orderProductPrice.productPriceSpecialStartDate = {
                        year: orderProductPrice.productPriceSpecialStartDate.getFullYear(),
                        month: orderProductPrice.productPriceSpecialStartDate.getMonth() + 1,
                        day: orderProductPrice.productPriceSpecialStartDate.getDate()
                    };
                }
                if (orderProductPrice.productPriceSpecialEndDate) {
                    orderProductPrice.productPriceSpecialEndDate = {
                        year: orderProductPrice.productPriceSpecialEndDate.getFullYear(),
                        month: orderProductPrice.productPriceSpecialEndDate.getMonth() + 1,
                        day: orderProductPrice.productPriceSpecialEndDate.getDate()
                    };
                }
                this.orderProductPriceModalRef(component, orderProductPrice);
            });
        } else {
            return this.orderProductPriceModalRef(component, new OrderProductPrice());
        }
    }

    orderProductPriceModalRef(component: Component, orderProductPrice: OrderProductPrice): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderProductPrice = orderProductPrice;
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
