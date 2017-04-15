import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomerOptionSet } from './customer-option-set.model';
import { CustomerOptionSetService } from './customer-option-set.service';
@Injectable()
export class CustomerOptionSetPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private customerOptionSetService: CustomerOptionSetService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerOptionSetService.find(id).subscribe((customerOptionSet) => {
                this.customerOptionSetModalRef(component, customerOptionSet);
            });
        } else {
            return this.customerOptionSetModalRef(component, new CustomerOptionSet());
        }
    }

    customerOptionSetModalRef(component: Component, customerOptionSet: CustomerOptionSet): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerOptionSet = customerOptionSet;
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
