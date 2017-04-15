import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomerOption } from './customer-option.model';
import { CustomerOptionService } from './customer-option.service';
@Injectable()
export class CustomerOptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private customerOptionService: CustomerOptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerOptionService.find(id).subscribe((customerOption) => {
                this.customerOptionModalRef(component, customerOption);
            });
        } else {
            return this.customerOptionModalRef(component, new CustomerOption());
        }
    }

    customerOptionModalRef(component: Component, customerOption: CustomerOption): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerOption = customerOption;
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
