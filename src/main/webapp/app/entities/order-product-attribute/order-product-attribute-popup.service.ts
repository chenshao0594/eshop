import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrderProductAttribute } from './order-product-attribute.model';
import { OrderProductAttributeService } from './order-product-attribute.service';
@Injectable()
export class OrderProductAttributePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderProductAttributeService: OrderProductAttributeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.orderProductAttributeService.find(id).subscribe((orderProductAttribute) => {
                this.orderProductAttributeModalRef(component, orderProductAttribute);
            });
        } else {
            return this.orderProductAttributeModalRef(component, new OrderProductAttribute());
        }
    }

    orderProductAttributeModalRef(component: Component, orderProductAttribute: OrderProductAttribute): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderProductAttribute = orderProductAttribute;
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
