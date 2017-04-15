import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Manufacturer } from './manufacturer.model';
import { ManufacturerPopupService } from './manufacturer-popup.service';
import { ManufacturerService } from './manufacturer.service';

@Component({
    selector: 'jhi-manufacturer-delete-dialog',
    templateUrl: './manufacturer-delete-dialog.component.html'
})
export class ManufacturerDeleteDialogComponent {

    manufacturer: Manufacturer;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private manufacturerService: ManufacturerService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['manufacturer']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.manufacturerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'manufacturerListModification',
                content: 'Deleted an manufacturer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-manufacturer-delete-popup',
    template: ''
})
export class ManufacturerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private manufacturerPopupService: ManufacturerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.manufacturerPopupService
                .open(ManufacturerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
