import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrderStatusHistory } from './order-status-history.model';
import { OrderStatusHistoryService } from './order-status-history.service';
@Injectable()
export class OrderStatusHistoryPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderStatusHistoryService: OrderStatusHistoryService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.orderStatusHistoryService.find(id).subscribe((orderStatusHistory) => {
                if (orderStatusHistory.dateAdded) {
                    orderStatusHistory.dateAdded = {
                        year: orderStatusHistory.dateAdded.getFullYear(),
                        month: orderStatusHistory.dateAdded.getMonth() + 1,
                        day: orderStatusHistory.dateAdded.getDate()
                    };
                }
                this.orderStatusHistoryModalRef(component, orderStatusHistory);
            });
        } else {
            return this.orderStatusHistoryModalRef(component, new OrderStatusHistory());
        }
    }

    orderStatusHistoryModalRef(component: Component, orderStatusHistory: OrderStatusHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderStatusHistory = orderStatusHistory;
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
