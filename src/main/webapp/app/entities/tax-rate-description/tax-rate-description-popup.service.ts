import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TaxRateDescription } from './tax-rate-description.model';
import { TaxRateDescriptionService } from './tax-rate-description.service';
@Injectable()
export class TaxRateDescriptionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private taxRateDescriptionService: TaxRateDescriptionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taxRateDescriptionService.find(id).subscribe((taxRateDescription) => {
                this.taxRateDescriptionModalRef(component, taxRateDescription);
            });
        } else {
            return this.taxRateDescriptionModalRef(component, new TaxRateDescription());
        }
    }

    taxRateDescriptionModalRef(component: Component, taxRateDescription: TaxRateDescription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.taxRateDescription = taxRateDescription;
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
