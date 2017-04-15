import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { MerchantLog } from './merchant-log.model';
import { MerchantLogPopupService } from './merchant-log-popup.service';
import { MerchantLogService } from './merchant-log.service';

@Component({
    selector: 'jhi-merchant-log-delete-dialog',
    templateUrl: './merchant-log-delete-dialog.component.html'
})
export class MerchantLogDeleteDialogComponent {

    merchantLog: MerchantLog;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private merchantLogService: MerchantLogService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['merchantLog']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.merchantLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'merchantLogListModification',
                content: 'Deleted an merchantLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-merchant-log-delete-popup',
    template: ''
})
export class MerchantLogDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantLogPopupService: MerchantLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.merchantLogPopupService
                .open(MerchantLogDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
