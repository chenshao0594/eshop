import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Customer } from './customer.model';
import { CustomerService } from './customer.service';
@Injectable()
export class CustomerPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private customerService: CustomerService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerService.find(id).subscribe((customer) => {
                if (customer.dateOfBirth) {
                    customer.dateOfBirth = {
                        year: customer.dateOfBirth.getFullYear(),
                        month: customer.dateOfBirth.getMonth() + 1,
                        day: customer.dateOfBirth.getDate()
                    };
                }
                if (customer.created_date) {
                    customer.created_date = {
                        year: customer.created_date.getFullYear(),
                        month: customer.created_date.getMonth() + 1,
                        day: customer.created_date.getDate()
                    };
                }
                if (customer.last_modified_date) {
                    customer.last_modified_date = {
                        year: customer.last_modified_date.getFullYear(),
                        month: customer.last_modified_date.getMonth() + 1,
                        day: customer.last_modified_date.getDate()
                    };
                }
                this.customerModalRef(component, customer);
            });
        } else {
            return this.customerModalRef(component, new Customer());
        }
    }

    customerModalRef(component: Component, customer: Customer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customer = customer;
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
