import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MerchantLog } from './merchant-log.model';
import { MerchantLogService } from './merchant-log.service';
@Injectable()
export class MerchantLogPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private merchantLogService: MerchantLogService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.merchantLogService.find(id).subscribe((merchantLog) => {
                this.merchantLogModalRef(component, merchantLog);
            });
        } else {
            return this.merchantLogModalRef(component, new MerchantLog());
        }
    }

    merchantLogModalRef(component: Component, merchantLog: MerchantLog): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.merchantLog = merchantLog;
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
