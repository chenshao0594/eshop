import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SalesOrder } from './sales-order.model';
import { SalesOrderService } from './sales-order.service';
@Injectable()
export class SalesOrderPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private salesOrderService: SalesOrderService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.salesOrderService.find(id).subscribe((salesOrder) => {
                if (salesOrder.orderDateFinished) {
                    salesOrder.orderDateFinished = {
                        year: salesOrder.orderDateFinished.getFullYear(),
                        month: salesOrder.orderDateFinished.getMonth() + 1,
                        day: salesOrder.orderDateFinished.getDate()
                    };
                }
                if (salesOrder.lastModified) {
                    salesOrder.lastModified = {
                        year: salesOrder.lastModified.getFullYear(),
                        month: salesOrder.lastModified.getMonth() + 1,
                        day: salesOrder.lastModified.getDate()
                    };
                }
                if (salesOrder.datePurchased) {
                    salesOrder.datePurchased = {
                        year: salesOrder.datePurchased.getFullYear(),
                        month: salesOrder.datePurchased.getMonth() + 1,
                        day: salesOrder.datePurchased.getDate()
                    };
                }
                if (salesOrder.createdDate) {
                    salesOrder.createdDate = {
                        year: salesOrder.createdDate.getFullYear(),
                        month: salesOrder.createdDate.getMonth() + 1,
                        day: salesOrder.createdDate.getDate()
                    };
                }
                if (salesOrder.lastModifiedDate) {
                    salesOrder.lastModifiedDate = {
                        year: salesOrder.lastModifiedDate.getFullYear(),
                        month: salesOrder.lastModifiedDate.getMonth() + 1,
                        day: salesOrder.lastModifiedDate.getDate()
                    };
                }
                this.salesOrderModalRef(component, salesOrder);
            });
        } else {
            return this.salesOrderModalRef(component, new SalesOrder());
        }
    }

    salesOrderModalRef(component: Component, salesOrder: SalesOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.salesOrder = salesOrder;
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
