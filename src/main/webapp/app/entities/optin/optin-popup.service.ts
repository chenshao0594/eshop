import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Optin } from './optin.model';
import { OptinService } from './optin.service';
@Injectable()
export class OptinPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private optinService: OptinService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.optinService.find(id).subscribe((optin) => {
                if (optin.startDate) {
                    optin.startDate = {
                        year: optin.startDate.getFullYear(),
                        month: optin.startDate.getMonth() + 1,
                        day: optin.startDate.getDate()
                    };
                }
                if (optin.endDate) {
                    optin.endDate = {
                        year: optin.endDate.getFullYear(),
                        month: optin.endDate.getMonth() + 1,
                        day: optin.endDate.getDate()
                    };
                }
                this.optinModalRef(component, optin);
            });
        } else {
            return this.optinModalRef(component, new Optin());
        }
    }

    optinModalRef(component: Component, optin: Optin): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.optin = optin;
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
