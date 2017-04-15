import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Zone } from './zone.model';
import { ZonePopupService } from './zone-popup.service';
import { ZoneService } from './zone.service';

@Component({
    selector: 'jhi-zone-delete-dialog',
    templateUrl: './zone-delete-dialog.component.html'
})
export class ZoneDeleteDialogComponent {

    zone: Zone;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private zoneService: ZoneService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['zone']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.zoneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'zoneListModification',
                content: 'Deleted an zone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-zone-delete-popup',
    template: ''
})
export class ZoneDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zonePopupService: ZonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.zonePopupService
                .open(ZoneDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
