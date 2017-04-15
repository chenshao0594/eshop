import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CustomerOption } from './customer-option.model';
import { CustomerOptionPopupService } from './customer-option-popup.service';
import { CustomerOptionService } from './customer-option.service';

@Component({
    selector: 'jhi-customer-option-delete-dialog',
    templateUrl: './customer-option-delete-dialog.component.html'
})
export class CustomerOptionDeleteDialogComponent {

    customerOption: CustomerOption;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerOptionService: CustomerOptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOption']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerOptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerOptionListModification',
                content: 'Deleted an customerOption'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-option-delete-popup',
    template: ''
})
export class CustomerOptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionPopupService: CustomerOptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.customerOptionPopupService
                .open(CustomerOptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
