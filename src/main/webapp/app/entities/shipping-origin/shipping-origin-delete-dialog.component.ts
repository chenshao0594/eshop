import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ShippingOrigin } from './shipping-origin.model';
import { ShippingOriginPopupService } from './shipping-origin-popup.service';
import { ShippingOriginService } from './shipping-origin.service';

@Component({
    selector: 'jhi-shipping-origin-delete-dialog',
    templateUrl: './shipping-origin-delete-dialog.component.html'
})
export class ShippingOriginDeleteDialogComponent {

    shippingOrigin: ShippingOrigin;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private shippingOriginService: ShippingOriginService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['shippingOrigin']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shippingOriginService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shippingOriginListModification',
                content: 'Deleted an shippingOrigin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shipping-origin-delete-popup',
    template: ''
})
export class ShippingOriginDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shippingOriginPopupService: ShippingOriginPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.shippingOriginPopupService
                .open(ShippingOriginDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
