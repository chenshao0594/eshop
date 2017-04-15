import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomerAttribute } from './customer-attribute.model';
import { CustomerAttributeService } from './customer-attribute.service';
@Injectable()
export class CustomerAttributePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private customerAttributeService: CustomerAttributeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerAttributeService.find(id).subscribe((customerAttribute) => {
                this.customerAttributeModalRef(component, customerAttribute);
            });
        } else {
            return this.customerAttributeModalRef(component, new CustomerAttribute());
        }
    }

    customerAttributeModalRef(component: Component, customerAttribute: CustomerAttribute): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerAttribute = customerAttribute;
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
