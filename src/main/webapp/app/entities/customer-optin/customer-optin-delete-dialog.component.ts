import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptin } from './customer-optin.model';
import { CustomerOptinPopupService } from './customer-optin-popup.service';
import { CustomerOptinService } from './customer-optin.service';

@Component({
    selector: 'jhi-customer-optin-delete-dialog',
    templateUrl: './customer-optin-delete-dialog.component.html'
})
export class CustomerOptinDeleteDialogComponent {

    customerOptin: CustomerOptin;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerOptinService: CustomerOptinService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptin']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerOptinService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerOptinListModification',
                content: 'Deleted an customerOptin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-optin-delete-popup',
    template: ''
})
export class CustomerOptinDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptinPopupService: CustomerOptinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerOptinPopupService
                .open(CustomerOptinDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
