import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TaxRate } from './tax-rate.model';
import { TaxRateService } from './tax-rate.service';
@Injectable()
export class TaxRatePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private taxRateService: TaxRateService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taxRateService.find(id).subscribe((taxRate) => {
                this.taxRateModalRef(component, taxRate);
            });
        } else {
            return this.taxRateModalRef(component, new TaxRate());
        }
    }

    taxRateModalRef(component: Component, taxRate: TaxRate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.taxRate = taxRate;
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
