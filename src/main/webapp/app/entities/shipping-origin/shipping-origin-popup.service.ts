import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ShippingOrigin } from './shipping-origin.model';
import { ShippingOriginService } from './shipping-origin.service';
@Injectable()
export class ShippingOriginPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private shippingOriginService: ShippingOriginService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.shippingOriginService.find(id).subscribe((shippingOrigin) => {
                this.shippingOriginModalRef(component, shippingOrigin);
            });
        } else {
            return this.shippingOriginModalRef(component, new ShippingOrigin());
        }
    }

    shippingOriginModalRef(component: Component, shippingOrigin: ShippingOrigin): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.shippingOrigin = shippingOrigin;
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
