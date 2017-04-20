import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Billing } from './billing.model';
import { BillingPopupService } from './billing-popup.service';
import { BillingService } from './billing.service';

@Component({
    selector: 'jhi-billing-delete-dialog',
    templateUrl: './billing-delete-dialog.component.html'
})
export class BillingDeleteDialogComponent {

    billing: Billing;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private billingService: BillingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['billing']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.billingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'billingListModification',
                content: 'Deleted an billing'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-billing-delete-popup',
    template: ''
})
export class BillingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private billingPopupService: BillingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.billingPopupService
                .open(BillingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
