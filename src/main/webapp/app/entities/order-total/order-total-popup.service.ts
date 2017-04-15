import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrderTotal } from './order-total.model';
import { OrderTotalService } from './order-total.service';
@Injectable()
export class OrderTotalPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderTotalService: OrderTotalService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.orderTotalService.find(id).subscribe((orderTotal) => {
                this.orderTotalModalRef(component, orderTotal);
            });
        } else {
            return this.orderTotalModalRef(component, new OrderTotal());
        }
    }

    orderTotalModalRef(component: Component, orderTotal: OrderTotal): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderTotal = orderTotal;
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
