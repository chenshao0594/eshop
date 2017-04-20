import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Delivery } from './delivery.model';
import { DeliveryPopupService } from './delivery-popup.service';
import { DeliveryService } from './delivery.service';

@Component({
    selector: 'jhi-delivery-delete-dialog',
    templateUrl: './delivery-delete-dialog.component.html'
})
export class DeliveryDeleteDialogComponent {

    delivery: Delivery;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private deliveryService: DeliveryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['delivery']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deliveryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deliveryListModification',
                content: 'Deleted an delivery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-delivery-delete-popup',
    template: ''
})
export class DeliveryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deliveryPopupService: DeliveryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.deliveryPopupService
                .open(DeliveryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
