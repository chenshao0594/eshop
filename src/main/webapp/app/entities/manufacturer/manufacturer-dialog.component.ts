import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Manufacturer } from './manufacturer.model';
import { ManufacturerPopupService } from './manufacturer-popup.service';
import { ManufacturerService } from './manufacturer.service';

@Component({
    selector: 'jhi-manufacturer-dialog',
    templateUrl: './manufacturer-dialog.component.html'
})
export class ManufacturerDialogComponent implements OnInit {

    manufacturer: Manufacturer;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private manufacturerService: ManufacturerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['manufacturer']);
        this.manufacturer = new Manufacturer();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.manufacturer.id !== undefined) {
            this.manufacturerService.update(this.manufacturer)
                .subscribe((res: Manufacturer) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.manufacturerService.create(this.manufacturer)
                .subscribe((res: Manufacturer) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Manufacturer) {
        this.eventManager.broadcast({ name: 'manufacturerListModification', content: 'OK'});
        this.isSaving = false;
        this.manufacturer = result;
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
}

@Component({
    selector: 'jhi-manufacturer-popup',
    template: ''
})
export class ManufacturerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private manufacturerPopupService: ManufacturerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.manufacturerPopupService
                    .open(ManufacturerDialogComponent, params['id']);
            } else {
                this.modalRef = this.manufacturerPopupService
                    .open(ManufacturerDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
