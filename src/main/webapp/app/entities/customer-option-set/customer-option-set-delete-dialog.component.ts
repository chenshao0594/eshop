import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptionSet } from './customer-option-set.model';
import { CustomerOptionSetPopupService } from './customer-option-set-popup.service';
import { CustomerOptionSetService } from './customer-option-set.service';

@Component({
    selector: 'jhi-customer-option-set-delete-dialog',
    templateUrl: './customer-option-set-delete-dialog.component.html'
})
export class CustomerOptionSetDeleteDialogComponent {

    customerOptionSet: CustomerOptionSet;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerOptionSetService: CustomerOptionSetService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptionSet']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerOptionSetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerOptionSetListModification',
                content: 'Deleted an customerOptionSet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-option-set-delete-popup',
    template: ''
})
export class CustomerOptionSetDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionSetPopupService: CustomerOptionSetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerOptionSetPopupService
                .open(CustomerOptionSetDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
