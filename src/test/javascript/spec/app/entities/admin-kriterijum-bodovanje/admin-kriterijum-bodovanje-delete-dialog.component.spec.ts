/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AdminKriterijumBodovanjeDeleteDialogComponent } from 'app/entities/admin-kriterijum-bodovanje/admin-kriterijum-bodovanje-delete-dialog.component';
import { AdminKriterijumBodovanjeService } from 'app/entities/admin-kriterijum-bodovanje/admin-kriterijum-bodovanje.service';

describe('Component Tests', () => {
    describe('AdminKriterijumBodovanje Management Delete Component', () => {
        let comp: AdminKriterijumBodovanjeDeleteDialogComponent;
        let fixture: ComponentFixture<AdminKriterijumBodovanjeDeleteDialogComponent>;
        let service: AdminKriterijumBodovanjeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AdminKriterijumBodovanjeDeleteDialogComponent]
            })
                .overrideTemplate(AdminKriterijumBodovanjeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdminKriterijumBodovanjeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdminKriterijumBodovanjeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
