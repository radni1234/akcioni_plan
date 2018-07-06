/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { ProjekatBodovanjeUpdateComponent } from 'app/entities/projekat-bodovanje/projekat-bodovanje-update.component';
import { ProjekatBodovanjeService } from 'app/entities/projekat-bodovanje/projekat-bodovanje.service';
import { ProjekatBodovanje } from 'app/shared/model/projekat-bodovanje.model';

describe('Component Tests', () => {
    describe('ProjekatBodovanje Management Update Component', () => {
        let comp: ProjekatBodovanjeUpdateComponent;
        let fixture: ComponentFixture<ProjekatBodovanjeUpdateComponent>;
        let service: ProjekatBodovanjeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [ProjekatBodovanjeUpdateComponent]
            })
                .overrideTemplate(ProjekatBodovanjeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjekatBodovanjeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjekatBodovanjeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProjekatBodovanje(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.projekatBodovanje = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProjekatBodovanje();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.projekatBodovanje = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
