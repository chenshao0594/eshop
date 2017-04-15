import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomerOptionDescription } from './customer-option-description.model';
import { CustomerOptionDescriptionService } from './customer-option-description.service';
@Injectable()
export class CustomerOptionDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private customerOptionDescriptionService: CustomerOptionDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerOptionDescriptionService.find(id).subscribe((customerOptionDescription) => {
                this.customerOptionDescriptionModalRef(component, customerOptionDescription);
            });
        } else {
            return this.customerOptionDescriptionModalRef(component, new CustomerOptionDescription());
        }
    }

    customerOptionDescriptionModalRef(component: Component, customerOptionDescription: CustomerOptionDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerOptionDescription = customerOptionDescription;
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
