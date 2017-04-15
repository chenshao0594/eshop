import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomerOptin } from './customer-optin.model';
import { CustomerOptinService } from './customer-optin.service';
@Injectable()
export class CustomerOptinPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private customerOptinService: CustomerOptinService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerOptinService.find(id).subscribe((customerOptin) => {
                if (customerOptin.optinDate) {
                    customerOptin.optinDate = {
                        year: customerOptin.optinDate.getFullYear(),
                        month: customerOptin.optinDate.getMonth() + 1,
                        day: customerOptin.optinDate.getDate()
                    };
                }
                this.customerOptinModalRef(component, customerOptin);
            });
        } else {
            return this.customerOptinModalRef(component, new CustomerOptin());
        }
    }

    customerOptinModalRef(component: Component, customerOptin: CustomerOptin): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerOptin = customerOptin;
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
