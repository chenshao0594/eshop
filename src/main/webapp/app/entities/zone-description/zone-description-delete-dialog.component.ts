import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ZoneDescription } from './zone-description.model';
import { ZoneDescriptionPopupService } from './zone-description-popup.service';
import { ZoneDescriptionService } from './zone-description.service';

@Component({
    selector: 'jhi-zone-description-delete-dialog',
    templateUrl: './zone-description-delete-dialog.component.html'
})
export class ZoneDescriptionDeleteDialogComponent {

    zoneDescription: ZoneDescription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private zoneDescriptionService: ZoneDescriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['zoneDescription']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.zoneDescriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'zoneDescriptionListModification',
                content: 'Deleted an zoneDescription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-zone-description-delete-popup',
    template: ''
})
export class ZoneDescriptionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private zoneDescriptionPopupService: ZoneDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.zoneDescriptionPopupService
                .open(ZoneDescriptionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
