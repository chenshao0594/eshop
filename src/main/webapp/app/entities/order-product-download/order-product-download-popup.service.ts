import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OrderProductDownload } from './order-product-download.model';
import { OrderProductDownloadService } from './order-product-download.service';
@Injectable()
export class OrderProductDownloadPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private orderProductDownloadService: OrderProductDownloadService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.orderProductDownloadService.find(id).subscribe((orderProductDownload) => {
                this.orderProductDownloadModalRef(component, orderProductDownload);
            });
        } else {
            return this.orderProductDownloadModalRef(component, new OrderProductDownload());
        }
    }

    orderProductDownloadModalRef(component: Component, orderProductDownload: OrderProductDownload): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderProductDownload = orderProductDownload;
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
