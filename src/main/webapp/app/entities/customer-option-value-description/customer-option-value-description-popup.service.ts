import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomerOptionValueDescription } from './customer-option-value-description.model';
import { CustomerOptionValueDescriptionService } from './customer-option-value-description.service';
@Injectable()
export class CustomerOptionValueDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private customerOptionValueDescriptionService: CustomerOptionValueDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerOptionValueDescriptionService.find(id).subscribe((customerOptionValueDescription) => {
                this.customerOptionValueDescriptionModalRef(component, customerOptionValueDescription);
            });
        } else {
            return this.customerOptionValueDescriptionModalRef(component, new CustomerOptionValueDescription());
        }
    }

    customerOptionValueDescriptionModalRef(component: Component, customerOptionValueDescription: CustomerOptionValueDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerOptionValueDescription = customerOptionValueDescription;
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
