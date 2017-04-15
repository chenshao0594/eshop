import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrderAccountProduct } from './order-account-product.model';
import { OrderAccountProductService } from './order-account-product.service';
@Injectable()
export class OrderAccountProductPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderAccountProductService: OrderAccountProductService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.orderAccountProductService.find(id).subscribe((orderAccountProduct) => {
                if (orderAccountProduct.orderAccountProductEndDate) {
                    orderAccountProduct.orderAccountProductEndDate = {
                        year: orderAccountProduct.orderAccountProductEndDate.getFullYear(),
                        month: orderAccountProduct.orderAccountProductEndDate.getMonth() + 1,
                        day: orderAccountProduct.orderAccountProductEndDate.getDate()
                    };
                }
                if (orderAccountProduct.orderAccountProductStartDate) {
                    orderAccountProduct.orderAccountProductStartDate = {
                        year: orderAccountProduct.orderAccountProductStartDate.getFullYear(),
                        month: orderAccountProduct.orderAccountProductStartDate.getMonth() + 1,
                        day: orderAccountProduct.orderAccountProductStartDate.getDate()
                    };
                }
                if (orderAccountProduct.orderAccountProductLastStatusDate) {
                    orderAccountProduct.orderAccountProductLastStatusDate = {
                        year: orderAccountProduct.orderAccountProductLastStatusDate.getFullYear(),
                        month: orderAccountProduct.orderAccountProductLastStatusDate.getMonth() + 1,
                        day: orderAccountProduct.orderAccountProductLastStatusDate.getDate()
                    };
                }
                if (orderAccountProduct.orderAccountProductAccountedDate) {
                    orderAccountProduct.orderAccountProductAccountedDate = {
                        year: orderAccountProduct.orderAccountProductAccountedDate.getFullYear(),
                        month: orderAccountProduct.orderAccountProductAccountedDate.getMonth() + 1,
                        day: orderAccountProduct.orderAccountProductAccountedDate.getDate()
                    };
                }
                if (orderAccountProduct.orderAccountProductEot) {
                    orderAccountProduct.orderAccountProductEot = {
                        year: orderAccountProduct.orderAccountProductEot.getFullYear(),
                        month: orderAccountProduct.orderAccountProductEot.getMonth() + 1,
                        day: orderAccountProduct.orderAccountProductEot.getDate()
                    };
                }
                this.orderAccountProductModalRef(component, orderAccountProduct);
            });
        } else {
            return this.orderAccountProductModalRef(component, new OrderAccountProduct());
        }
    }

    orderAccountProductModalRef(component: Component, orderAccountProduct: OrderAccountProduct): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderAccountProduct = orderAccountProduct;
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
