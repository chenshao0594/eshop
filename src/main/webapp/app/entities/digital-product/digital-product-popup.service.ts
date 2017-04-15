import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DigitalProduct } from './digital-product.model';
import { DigitalProductService } from './digital-product.service';
@Injectable()
export class DigitalProductPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private digitalProductService: DigitalProductService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.digitalProductService.find(id).subscribe((digitalProduct) => {
                this.digitalProductModalRef(component, digitalProduct);
            });
        } else {
            return this.digitalProductModalRef(component, new DigitalProduct());
        }
    }

    digitalProductModalRef(component: Component, digitalProduct: DigitalProduct): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.digitalProduct = digitalProduct;
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
