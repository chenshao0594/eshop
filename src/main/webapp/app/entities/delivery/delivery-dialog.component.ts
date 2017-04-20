import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Delivery } from './delivery.model';
import { DeliveryPopupService } from './delivery-popup.service';
import { DeliveryService } from './delivery.service';
import { Country, CountryService } from '../country';

@Component({
    selector: 'jhi-delivery-dialog',
    templateUrl: './delivery-dialog.component.html'
})
export class DeliveryDialogComponent implements OnInit {

    delivery: Delivery;
    authorities: any[];
    isSaving: boolean;

    countries: Country[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private deliveryService: DeliveryService,
        private countryService: CountryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['delivery']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.countryService.query().subscribe(
            (res: Response) => { this.countries = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.delivery.id !== undefined) {
            this.deliveryService.update(this.delivery)
                .subscribe((res: Delivery) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.deliveryService.create(this.delivery)
                .subscribe((res: Delivery) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Delivery) {
        this.eventManager.broadcast({ name: 'deliveryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-delivery-popup',
    template: ''
})
export class DeliveryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deliveryPopupService: DeliveryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.deliveryPopupService
                    .open(DeliveryDialogComponent, params['id']);
            } else {
                this.modalRef = this.deliveryPopupService
                    .open(DeliveryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
