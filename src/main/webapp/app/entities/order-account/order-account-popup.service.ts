import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrderAccount } from './order-account.model';
import { OrderAccountService } from './order-account.service';
@Injectable()
export class OrderAccountPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderAccountService: OrderAccountService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.orderAccountService.find(id).subscribe((orderAccount) => {
                if (orderAccount.orderAccountStartDate) {
                    orderAccount.orderAccountStartDate = {
                        year: orderAccount.orderAccountStartDate.getFullYear(),
                        month: orderAccount.orderAccountStartDate.getMonth() + 1,
                        day: orderAccount.orderAccountStartDate.getDate()
                    };
                }
                if (orderAccount.orderAccountEndDate) {
                    orderAccount.orderAccountEndDate = {
                        year: orderAccount.orderAccountEndDate.getFullYear(),
                        month: orderAccount.orderAccountEndDate.getMonth() + 1,
                        day: orderAccount.orderAccountEndDate.getDate()
                    };
                }
                this.orderAccountModalRef(component, orderAccount);
            });
        } else {
            return this.orderAccountModalRef(component, new OrderAccount());
        }
    }

    orderAccountModalRef(component: Component, orderAccount: OrderAccount): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderAccount = orderAccount;
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
