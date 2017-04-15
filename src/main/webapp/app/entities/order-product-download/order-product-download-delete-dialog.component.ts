import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { OrderProductDownload } from './order-product-download.model';
import { OrderProductDownloadPopupService } from './order-product-download-popup.service';
import { OrderProductDownloadService } from './order-product-download.service';

@Component({
    selector: 'jhi-order-product-download-delete-dialog',
    templateUrl: './order-product-download-delete-dialog.component.html'
})
export class OrderProductDownloadDeleteDialogComponent {

    orderProductDownload: OrderProductDownload;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderProductDownloadService: OrderProductDownloadService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderProductDownload']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderProductDownloadService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderProductDownloadListModification',
                content: 'Deleted an orderProductDownload'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-product-download-delete-popup',
    template: ''
})
export class OrderProductDownloadDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderProductDownloadPopupService: OrderProductDownloadPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.orderProductDownloadPopupService
                .open(OrderProductDownloadDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
