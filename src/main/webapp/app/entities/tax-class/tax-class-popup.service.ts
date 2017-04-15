import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TaxClass } from './tax-class.model';
import { TaxClassService } from './tax-class.service';
@Injectable()
export class TaxClassPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private taxClassService: TaxClassService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taxClassService.find(id).subscribe((taxClass) => {
                this.taxClassModalRef(component, taxClass);
            });
        } else {
            return this.taxClassModalRef(component, new TaxClass());
        }
    }

    taxClassModalRef(component: Component, taxClass: TaxClass): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.taxClass = taxClass;
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
