import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { MerchantStore } from './merchant-store.model';
import { MerchantStorePopupService } from './merchant-store-popup.service';
import { MerchantStoreService } from './merchant-store.service';

@Component({
    selector: 'jhi-merchant-store-delete-dialog',
    templateUrl: './merchant-store-delete-dialog.component.html'
})
export class MerchantStoreDeleteDialogComponent {

    merchantStore: MerchantStore;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private merchantStoreService: MerchantStoreService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['merchantStore']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.merchantStoreService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'merchantStoreListModification',
                content: 'Deleted an merchantStore'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-merchant-store-delete-popup',
    template: ''
})
export class MerchantStoreDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantStorePopupService: MerchantStorePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.merchantStorePopupService
                .open(MerchantStoreDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
